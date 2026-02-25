#include <cstdio>
#include <cstdlib>
#include <cuda_runtime.h>

#define N 1500
#define TILE 16

#define CUDA_CHECK(call) do {                                   \
    cudaError_t err = call;                                     \
    if (err != cudaSuccess) {                                   \
        printf("CUDA error %s at %s:%d\n",                      \
            cudaGetErrorString(err), __FILE__, __LINE__);       \
        exit(1);                                                \
    }                                                           \
} while (0)

double get_ms() {
    struct timespec ts;
    clock_gettime(CLOCK_MONOTONIC, &ts);
    return (ts.tv_sec * 1e3) + (ts.tv_nsec / 1e6);
}

__global__
void matmul_tiled(const float* A, const float* B, float* C, int n) {
    int row = blockIdx.y * TILE + threadIdx.y;
    int col = blockIdx.x * TILE + threadIdx.x;

    float val = 0.0f;

    __shared__ float As[TILE][TILE];
    __shared__ float Bs[TILE][TILE];

    for (int t = 0; t < (n + TILE - 1) / TILE; t++) {
        int colA = t * TILE + threadIdx.x;
        int rowB = t * TILE + threadIdx.y;

        As[threadIdx.y][threadIdx.x] =
            (row < n && colA < n) ? A[row * n + colA] : 0;

        Bs[threadIdx.y][threadIdx.x] =
            (rowB < n && col < n) ? B[rowB * n + col] : 0;

        __syncthreads();

        for (int k = 0; k < TILE; k++)
            val += As[threadIdx.y][k] * Bs[k][threadIdx.x];

        __syncthreads();
    }

    if (row < n && col < n)
        C[row * n + col] = val;
}

int main() {
    printf("GPU-Only Matrix Multiplication (N = %d)\n", N);

    size_t bytes = N * N * sizeof(float);

    float *h_A = (float*)malloc(bytes);
    float *h_B = (float*)malloc(bytes);
    float *h_C = (float*)malloc(bytes);

    for (int i = 0; i < N * N; i++) {
        h_A[i] = (float)(i % 100) / 10.0f;
        h_B[i] = (float)((i * 2) % 100) / 10.0f;
    }

    float *d_A, *d_B, *d_C;
    CUDA_CHECK(cudaMalloc(&d_A, bytes));
    CUDA_CHECK(cudaMalloc(&d_B, bytes));
    CUDA_CHECK(cudaMalloc(&d_C, bytes));

    CUDA_CHECK(cudaMemcpy(d_A, h_A, bytes, cudaMemcpyHostToDevice));
    CUDA_CHECK(cudaMemcpy(d_B, h_B, bytes, cudaMemcpyHostToDevice));

    dim3 block(TILE, TILE);
    dim3 grid((N + TILE - 1) / TILE, (N + TILE - 1) / TILE);

    cudaEvent_t start, stop;
    cudaEventCreate(&start);
    cudaEventCreate(&stop);

    cudaEventRecord(start);
    matmul_tiled<<<grid, block>>>(d_A, d_B, d_C, N);
    cudaEventRecord(stop);

    cudaEventSynchronize(stop);

    float gpu_ms = 0;
    cudaEventElapsedTime(&gpu_ms, start, stop);

    printf("CUDA kernel time: %.3f ms\n", gpu_ms);

    CUDA_CHECK(cudaMemcpy(h_C, d_C, bytes, cudaMemcpyDeviceToHost));

    cudaFree(d_A);
    cudaFree(d_B);
    cudaFree(d_C);
    free(h_A);
    free(h_B);
    free(h_C);

    return 0;
}
