<?php
header('Content-Type: application/json');

$response = array();

if ($_SERVER["REQUEST_METHOD"] == "POST") {
    $name = $_POST["name"];
    $username = $_POST["username"];
    $password = $_POST["password"];

    // Validate input
    if (empty($name) || empty($username) || empty($password)) {
        $response["success"] = false;
        $response["message"] = "Semua field harus diisi";
    } else {
        // Check if username already exists
        $checkStmt = $conn->prepare("SELECT username FROM user WHERE username = ?");
        $checkStmt->bind_param("s", $username);
        $checkStmt->execute();
        $result = $checkStmt->get_result();

        if ($result->num_rows > 0) {
            $response["success"] = false;
            $response["message"] = "Username sudah digunakan";
        } else {
            // Hash password for security
            $hashedPassword = password_hash($password, PASSWORD_DEFAULT);

            // Insert new user with default values
            $cardNumber = "12345678"; // Default value, adjust as needed
            $saldo = 0; // Default saldo
            $status = "active";
            $createdAt = date("Y-m-d H:i:s");

            $stmt = $conn->prepare("INSERT INTO user (name, username, password, card_number, saldo, status, created_at) VALUES (?, ?, ?, ?, ?, ?, ?)");
            $stmt->bind_param("sssssis", $name, $username, $hashedPassword, $cardNumber, $saldo, $status, $createdAt);

            if ($stmt->execute()) {
                $response["success"] = true;
                $response["message"] = "Registrasi berhasil";
            } else {
                $response["success"] = false;
                $response["message"] = "Error: " . $conn->error;
            }

            $stmt->close();
        }

        $checkStmt->close();
    }
} else {
    $response["success"] = false;
    $response["message"] = "Metode tidak diizinkan";
}

echo json_encode($response);
?>