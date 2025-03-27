<?php
require_once 'mysqli.php';

header("Content-Type: application/json; charset=UTF-8");
header("Access-Control-Allow-Origin: *");
header("Access-Control-Allow-Methods: POST, GET, OPTIONS");

$response = array();

if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    $data = json_decode(file_get_contents("php://input"), true);

    if (!$data) {
        http_response_code(400);
        echo json_encode(["responses" => false, "message" => "Invalid JSON"]);
        exit();
    }

    $wipBoxId = $data['wipBoxId'] ?? null;
    $itemList = $data['itemInBoxList'] ?? [];

    if (!empty($itemList)) {
        foreach ($itemList as $item) {
            $itemCode = $item['itemCode'];
            $quantity = $item['quantity'];

            $sql = $MySQLiconn->query("UPDATE wip_box_detail 
                SET quantity = '$quantity',
                    status = CASE WHEN '$quantity' = 0 THEN 0 ELSE status END
                WHERE wipBoxId = '$wipBoxId' 
                AND itemCode = '$itemCode'
            ");

            if (!$sql) {
                error_log("SQL Error: " . $MySQLiconn->error);
            }
        }
    }

    if ($MySQLiconn->affected_rows > 0) {
        $response["responses"] = true;
        $response["message"] = "Data berhasil diperbarui";
    } else {
        $response["responses"] = false;
        $response["message"] = "Tidak ada data yang diperbarui";
    }

    error_log(json_encode($response));
    echo json_encode($response);
}
?>
