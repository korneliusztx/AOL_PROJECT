<?php
include_once "koneksi.php";
header("Content-Type: application/json");

// Ambil dan sanitasi input
$device = isset($_GET['hex']) ? trim($_GET['hex']) : '';
$card = isset($_GET['card']) ? trim($_GET['card']) : '';

if (empty($card) || empty($device)) {
    echo json_encode([
        'status' => 'error',
        'message' => 'Parameter tidak lengkap'
    ]);
    exit;
}

// Cek alat berdasarkan hex
$stmt = $conn->prepare("SELECT * FROM alat WHERE device_number = ?");
$stmt->bind_param("s", $device);
$stmt->execute();
$result = $stmt->get_result();

if ($result && $result->num_rows > 0) {
    $alat = $result->fetch_assoc();
    $tarif = (int)$alat['tarif'];

    // Cek user berdasarkan card_number
    $stmt2 = $conn->prepare("SELECT * FROM user WHERE card_number = ?");
    $stmt2->bind_param("s", $card);
    $stmt2->execute();
    $result2 = $stmt2->get_result();

    if ($result2 && $result2->num_rows > 0) {
        $user = $result2->fetch_assoc();
        $saldo = (int)$user['saldo'];

        if ($saldo >= $tarif) {
            // Update saldo
            $newSaldo = $saldo - $tarif;
            $stmt3 = $conn->prepare("UPDATE user SET saldo = ? WHERE card_number = ?");
            $stmt3->bind_param("is", $newSaldo, $card);
            $stmt3->execute();

            echo json_encode([
                'status' => 'success',
                'message' => 'Akses diterima! Saldo awal: ' . $user['saldo'] . ' & saldo akhir: ' . $newSaldo,
                'data' => [
                    'name' => $user['name'],
                    'saldo_sekarang' => $newSaldo
                ]
            ]);
        } else {
            echo json_encode([
                'status' => 'error',
                'message' => 'Saldo tidak mencukupi. Saldo anda: ' . $user['saldo']
            ]);
        }
    } else {
        echo json_encode([
            'status' => 'error',
            'message' => 'Kartu tidak ditemukan'
        ]);
    }
} else {
    echo json_encode([
        'status' => 'error',
        'message' => 'Alat tidak ditemukan'
    ]);
}

$stmt->close();
$conn->close();
