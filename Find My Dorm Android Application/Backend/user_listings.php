<?php
include 'db.php';

$phone = $_GET['phone'];
// added short_description to the SELECT
$sql   = "SELECT id, dorm_name, price, location, short_description FROM dorms WHERE user_phone = '$phone'";
$result = $conn->query($sql);

$dorms = [];
if ($result && $result->num_rows > 0) {
    while ($row = $result->fetch_assoc()) {
        $row['id']    = (int)  $row['id'];
        $row['price'] = (float)$row['price'];
        
        $dorms[] = $row;
    }
    header('Content-Type: application/json');
    echo json_encode(["success" => true, "dorms" => $dorms]);
} else {
    header('Content-Type: application/json');
    echo json_encode(["success" => false, "message" => "No listings found"]);
}
$conn->close();
?>
