<?php
include 'koneksi.php'; // koneksi ke database

$username = $_POST['username'];
$password = $_POST['password'];

$sql = "SELECT * FROM user WHERE username='$username' AND password='$password'";
$result = mysqli_query($conn, $sql);

if (mysqli_num_rows($result) === 1) {
    $row = mysqli_fetch_assoc($result);
    echo json_encode([
        "success" => true,
        "name" => $row["name"],
        "saldo" => $row["saldo"]
    ]);
} else {
    echo json_encode(["success" => false, "message" => "Email atau password salah"]);
}
?>
