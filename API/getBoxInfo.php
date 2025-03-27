<?php
require_once 'mysqli.php';

    $response = array();

    if ($_SERVER['REQUEST_METHOD'] == 'POST') {
        
        $boxCode = $_POST['boxCode'];

        $sql = $MySQLiconn->query("SELECT wb.wipBoxId, wbd.wipBoxDetailId, wb.boxCode, wb.locationId, wb.wipLineNumber, wb.stack, wb.status
                    FROM wip_box wb
                    JOIN wip_box_detail wbd ON wbd.wipBoxId = wb.wipBoxId
                    WHERE wb.boxCode = '$boxCode'
                    AND wb.status != 3 
                    GROUP BY wipBoxId");
        
      if(mysqli_num_rows($sql) > 0) {
        while($row = $sql->fetch_array()){
             $response["responses"] = TRUE;
             $response["wipBoxId"] = (int) $row["wipBoxId"];
             $response["wipBoxDetailId"] = (int) $row["wipBoxDetailId"];
             $response["boxCode"] = $row["boxCode"];
             $response["locationId"] = (int) $row["locationId"];
             $response["wipLineNumber"] = (int) $row["wipLineNumber"];
             $response["stack"] = (int) $row["stack"];
             $response["status"] = (int) $row["status"];
        }
            echo json_encode($response);
            
        }else{
            $response["responses"] = FALSE;
            echo json_encode($response);
        }
    }

?>
