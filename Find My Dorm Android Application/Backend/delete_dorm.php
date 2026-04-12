<?php
include 'db.php';

$dorm_id = $_POST['dorm_id'];
$conn->query("DELETE FROM dorm_images WHERE dorm_id = $dorm_id");
$sql = "DELETE FROM dorms WHERE id = $dorm_id";

if ($conn->query($sql)) {
    echo json_encode(["success" => true, "message" => "Dorm deleted successfully"]);
} else {
    echo json_encode(["success" => false, "message" => "Failed to delete dorm"]);
}

$conn->close();
?>
