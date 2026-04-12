<?php
include 'db.php';

if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    $dorm_id = $_POST['dorm_id'];

    if (!empty($_FILES['images'])) {
        $files = $_FILES['images'];

        for ($i = 0; $i < count($files['name']); $i++) {
            $filename = basename($files['name'][$i]);
            $target_file = "uploads/" . time() . "_" . $filename;

            if (move_uploaded_file($files['tmp_name'][$i], $target_file)) {
                $stmt = $conn->prepare("INSERT INTO dorm_images (dorm_id, image_path) VALUES (?, ?)");
                $stmt->bind_param("is", $dorm_id, $target_file);
                $stmt->execute();
                $stmt->close();
            }
        }

        echo json_encode(["success" => true]);
    } else {
        echo json_encode(["success" => false, "error" => "No images uploaded"]);
    }

    $conn->close();
}
?>
