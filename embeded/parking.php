<?php
header("Content-Type: application/json");
$koneksi = mysqli_connect("localhost","root", "", "parking");
if (!$koneksi) {
    die("Koneksi gagal: " . mysqli_connect_error());
}


$hex = $_GET['SN'] ?? null;
if (is_null($hex)) {
    echo json_encode(array("error" => "Parameter 'hex' tidak ditemukan."));
    exit;
}
$get_alat = mysqli_query($koneksi, "SELECT * FROM alat WHERE no_alat = '$hex'");
if (mysqli_num_rows($get_alat) > 0) {
    $alat = mysqli_fetch_assoc($get_alat);
    echo json_encode($alat);
} else {
    echo json_encode(array("error" => "Alat tidak ditemukan."));
}