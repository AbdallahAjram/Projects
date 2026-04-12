<?php
include 'db.php';

if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    $user_phone        = $_POST['user_phone'];
    $dorm_name         = $_POST['dorm_name'];
    $price             = $_POST['price'];
    $short_description = $_POST['short_description'];
    $location          = $_POST['location'];

    $stmt = $conn->prepare("
        INSERT INTO dorms
            (user_phone, dorm_name, price, short_description, location)
        VALUES
            (?, ?, ?, ?, ?)
    ");
    // types: s = string, d = double
    $stmt->bind_param("ssdss",
        $user_phone,
        $dorm_name,
        $price,
        $short_description,
        $location
    );

    if ($stmt->execute()) {
        echo json_encode([
            "success" => true,
            "dorm_id" => $stmt->insert_id
        ]);
    } else {
        echo json_encode([
            "success" => false,
            "error"   => $conn->error
        ]);
    }

    $stmt->close();
    $conn->close();
}
?>
