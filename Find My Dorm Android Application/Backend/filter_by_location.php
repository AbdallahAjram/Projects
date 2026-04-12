<?php
include 'db.php';

$location = $_GET['location'];

$sql = "SELECT id AS dorm_id, dorm_name, price, location FROM dorms WHERE location = '$location'";
$result = $conn->query($sql);

$dorms = [];

if ($result && $result->num_rows > 0) {
    while ($row = $result->fetch_assoc()) {
        $dorm_id = $row['dorm_id'];

        $img_sql = "SELECT image_path FROM dorm_images WHERE dorm_id = $dorm_id";
        $img_result = $conn->query($img_sql);

        $images = [];
        if ($img_result && $img_result->num_rows > 0) {
            while ($img_row = $img_result->fetch_assoc()) {
                $images[] = $img_row['image_path'];
            }
        }

        $row['images'] = $images;
        $dorms[] = $row;
    }

    echo json_encode(["success" => true, "dorms" => $dorms]);
} else {
    echo json_encode(["success" => false, "message" => "No dorms found in this location"]);
}

$conn->close();
?>
