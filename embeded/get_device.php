<?php
include_once "koneksi.php";
header("Content-Type: application/json");

// Ambil dan sanitasi input
$hex = isset($_GET['hex']) ? trim($_GET['hex']) : '';

if (empty($hex)) {
    echo json_encode([
        'status' => 'error',
        'message' => 'Hex number is empty'
    ]);
    exit;
}

// Gunakan prepared statement untuk keamanan
$stmt = $conn->prepare("SELECT * FROM alat WHERE device_number = ?");
$stmt->bind_param("s", $hex);
$stmt->execute();
$result = $stmt->get_result();

if ($result && $result->num_rows > 0) {
    $data = $result->fetch_assoc();
    echo json_encode([
        'status' => 'success',
        'data' => $data
    ]);
} else {
    echo json_encode([
        'status' => 'error',
        'message' => 'Device not found'
    ]);
}

$stmt->close();
$conn->close();
