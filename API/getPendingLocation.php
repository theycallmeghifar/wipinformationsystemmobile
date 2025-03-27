<?php
require_once 'mysqli.php';

    $response = array();

    if ($_SERVER['REQUEST_METHOD'] == 'POST') {
        
        $line = $_POST['line'];

        $sql = $MySQLiconn->query("SELECT locationId, area, line
                    FROM location
                    WHERE line like '%$line%'
                    AND status = 1");
        
      if(mysqli_num_rows($sql) > 0) {
        while($row = $sql->fetch_array()){
             $response["responses"] = TRUE;
             $response["locationId"] = (int) $row["locationId"];
             $response["area"] = $row["area"];
             $response["line"] = $row["line"];
        }
            echo json_encode($response);
            
        }else{
            $response["responses"] = FALSE;
            echo json_encode($response);
        }
    }

?>
