<?php
require_once 'mysqli.php';

    $response = array();

    if ($_SERVER['REQUEST_METHOD'] == 'POST') {
        $wipBoxId = $_POST['wipBoxId'];
        $locationId = $_POST['locationId'];
        $wipLineNumber = $_POST['wipLineNumber'];
        $stack = $_POST['stack'];
        $modifiedBy = $_POST['modifiedBy'];

        $sql = $MySQLiconn->query("UPDATE wip_box
                    SET locationId = '$locationId',
                        wipLineNumber = '$wipLineNumber',
                        stack = '$stack',
                        modifiedBy = '$modifiedBy',
                        modifiedDate = NOW()
                    WHERE wipBoxId = '$wipBoxId'");

        // Periksa apakah update berhasil
        if ($sql) {
            if ($MySQLiconn->affected_rows > 0) {
                $response["responses"] = TRUE;
                $response["message"] = "Data berhasil diperbarui";
            } else {
                $response["responses"] = FALSE;
                $response["message"] = "Tidak ada data yang diperbarui";
            }
        } else {
            $response["responses"] = FALSE;
            $response["message"] = "Kesalahan SQL: " . $MySQLiconn->error;
        }

        echo json_encode($response);
    }

?>
