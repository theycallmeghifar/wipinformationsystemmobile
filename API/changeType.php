<?php
require_once 'mysqli.php';

header("Content-Type: application/json; charset=UTF-8");
header("Access-Control-Allow-Origin: *");
header("Access-Control-Allow-Methods: POST, GET, OPTIONS");
header("Access-Control-Allow-Headers: Content-Type");

mysqli_report(MYSQLI_REPORT_ERROR | MYSQLI_REPORT_STRICT); // Debugging error MySQL

if ($_SERVER['REQUEST_METHOD'] == 'OPTIONS') {
    http_response_code(200);
    exit();
}

$response = array();

if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    // Ambil data JSON dari body
    $wipBoxId = $_POST['wipBoxId'];
    $locationId = $_POST['locationId'];
    $wipLineNumber = $_POST['wipLineNumber'];
    $stack = $_POST['stack'];
    $modifiedBy = $_POST['modifiedBy'];
    $status = $_POST['status'];

    if ($wipBoxId === null || $locationId === null || $wipLineNumber === null || 
    $stack === null || $modifiedBy === null || $modifiedBy === '') {
        http_response_code(400);
        echo json_encode(["responses" => false, "message" => "Semua parameter harus diisi"]);
        exit();
    }

    // Gunakan prepared statement untuk keamanan
    $stmt = $MySQLiconn->prepare("UPDATE wip_box 
        SET locationId = ?, 
            wipLineNumber = ?, 
            stack = ?, 
            status = ?, 
            modifiedBy = ?, 
            modifiedDate = NOW()
        WHERE wipBoxId = ?");

    $stmt->bind_param("iiiisi", $locationId, $wipLineNumber, $stack, $status, $modifiedBy, $wipBoxId);
    $success = $stmt->execute();

    if ($success) {
        if ($stmt->affected_rows > 0) {
            $response["responses"] = true;
            $response["message"] = "Data berhasil diperbarui";
        } else {
            $response["responses"] = false;
            $response["message"] = "Tidak ada data yang diperbarui";
        }
    } else {
        error_log("SQL Error: " . $stmt->error);
        $response["responses"] = false;
        $response["message"] = "Kesalahan SQL";
    }

    echo json_encode($response);
}
?>
