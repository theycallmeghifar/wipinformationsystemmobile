<?php
require_once 'mysqli.php';

$response = array();

if ($_SERVER['REQUEST_METHOD'] == 'GET') {
    $sql = $MySQLiconn->query("SELECT itemCode, itemName FROM item WHERE status = 1");

    if (mysqli_num_rows($sql) > 0) {
        $response["responses"] = true;
        $response["items"] = array();

        while ($row = $sql->fetch_assoc()) {
            $item = array(
                "itemCode" => $row["itemCode"],
                "itemName" => $row["itemName"]
            );
            array_push($response["items"], $item);
        }
    } else {
        $response["responses"] = false;
        $response["items"] = [];
    }

    echo json_encode($response);
}
?>
