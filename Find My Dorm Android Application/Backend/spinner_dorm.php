<?php
include 'db.php';

$sql = "SELECT DISTINCT location FROM dorms WHERE location IS NOT NULL AND location != ''";
$result = $conn->query($sql);

$locations = [];

if ($result && $result->num_rows > 0) {
    while ($row = $result->fetch_assoc()) {
        $locations[] = $row['location'];
    }
    echo json_encode(["success" => true, "locations" => $locations]);
} else {
    echo json_encode(["success" => false, "message" => "No locations found"]);
}

$conn->close();
?>
