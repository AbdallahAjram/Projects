<?php
include 'db.php';


$dorm_id = intval($_GET['dorm_id']);

$sql = "SELECT 
            dorms.dorm_name,
            dorms.price,
            dorms.location,
            dorms.short_description,
            users.name AS owner_name,
            users.phone AS owner_phone
        FROM dorms
        JOIN users ON dorms.user_phone = users.phone
        WHERE dorms.id = $dorm_id";

$result = $conn->query($sql);

if ($result && $result->num_rows > 0) {
    $dorm = $result->fetch_assoc();

    $img_sql = "SELECT image_path FROM dorm_images WHERE dorm_id = $dorm_id";
    $img_result = $conn->query($img_sql);

    $images = [];
    if ($img_result && $img_result->num_rows > 0) {
        while ($img_row = $img_result->fetch_assoc()) {
            $images[] = $img_row['image_path'];
        }
    }

    $dorm['images'] = $images;

    echo json_encode(["success" => true, "dorm" => $dorm]);
} else {
    echo json_encode(["success" => false, "message" => "Dorm not found"]);
}

$conn->close();
?>
