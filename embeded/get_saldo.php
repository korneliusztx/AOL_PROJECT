<?php
include_once "koneksi.php";
header("Content-Type: application/json");

// Ambil dan sanitasi input
$card = isset($_GET['card']) ? trim($_GET['card']) : '';

if (empty($card)) {
    echo json_encode([
        'status' => 'error',
        'message' => 'Parameter card tidak ditemukan'
    ]);
    exit;
}

// Query untuk mengambil saldo
$stmt = $conn->prepare("SELECT name, saldo FROM user WHERE card_number = ?");
$stmt->bind_param("s", $card);
$stmt->execute();
$result = $stmt->get_result();

if ($result && $result->num_rows > 0) {
    $user = $result->fetch_assoc();
    echo json_encode([
        'status' => 'success',
        'data' => [
            'name' => $user['name'],
            'saldo' => $user['saldo']
        ]
    ]);
} else {
    echo json_encode([
        'status' => 'error',
        'message' => 'Kartu tidak ditemukan'
    ]);
}

$stmt->close();
$conn->close();
?>
