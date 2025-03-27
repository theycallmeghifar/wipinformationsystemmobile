<?php
require_once 'mysqli.php';

    $response = array();

    if ($_SERVER['REQUEST_METHOD'] == 'POST') {
        
        $area = $_POST['area'];

        $sql = $MySQLiconn->query("SELECT locationId, area, line
                    FROM location
                    WHERE area like '%$area%'
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
