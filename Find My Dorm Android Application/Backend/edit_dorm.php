<?php
include 'db.php';

$dorm_id     = $_POST['dorm_id'];
$price       = $_POST['price'];
$location    = $_POST['location'];
$description = $_POST['description'];

// update the short_description field
$sql = "UPDATE dorms 
        SET price = '$price',
            location = '$location',
            short_description = '$description'
        WHERE id = $dorm_id";

if ($conn->query($sql)) {
    echo json_encode(["success" => true, "message" => "Dorm updated successfully"]);
} else {
    echo json_encode(["success" => false, "message" => "Failed to update dorm"]);
}
$conn->close();
?>
