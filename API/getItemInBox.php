<?php
require_once 'mysqli.php';

$response = array();
$response["responses"] = false; // Default response jika tidak ada data
$response["data"] = array(); // Array untuk menyimpan banyak baris

if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    
    $wipBoxId = $_POST['wipBoxId'];

    $sql = $MySQLiconn->query("SELECT wbd.itemCode, i.itemName, wbd.quantity
                FROM wip_box_detail wbd
                JOIN item i ON i.itemCode = wbd.itemCode
                WHERE wipBoxId = '$wipBoxId' ");
    
    if ($sql->num_rows > 0) {
        $response["responses"] = true;

        while ($row = $sql->fetch_assoc()) {
            $response["data"][] = array(
                "itemCode" => $row["itemCode"],
                "itemName" => $row["itemName"],
                "quantity" => (int) $row["quantity"]
            );
        }
    }

    echo json_encode($response);
}
?>
