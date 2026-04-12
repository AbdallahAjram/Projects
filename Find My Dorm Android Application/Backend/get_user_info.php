<?php
include 'db.php';


$phone = $_GET['phone'];

$sql = "SELECT name, phone FROM users WHERE phone = '$phone'";
$result = $conn->query($sql);

if ($result && $result->num_rows > 0) {
    $user = $result->fetch_assoc();

    $count_sql = "SELECT COUNT(*) AS dorm_count FROM dorms WHERE user_phone = '$phone'";
    $count_result = $conn->query($count_sql);

    $dorm_count = 0;
    if ($count_result && $count_result->num_rows > 0) {
        $count_row = $count_result->fetch_assoc();
        $dorm_count = $count_row['dorm_count'];
    }

    $user['dorm_count'] = $dorm_count;

    echo json_encode(["success" => true, "user" => $user]);
} else {
    echo json_encode(["success" => false, "message" => "User not found"]);
}

$conn->close();
?>  
