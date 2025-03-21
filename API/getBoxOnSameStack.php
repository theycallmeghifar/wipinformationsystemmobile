<?php
require_once 'mysqli.php';

    $response = array();

    if ($_SERVER['REQUEST_METHOD'] == 'POST') {
        
        $locationId = $_POST['locationId'];
        $wipLineNumber = $_POST['wipLineNumber'];
        $stack = $_POST['stack'];

        $sql = $MySQLiconn->query("SELECT * 
                    FROM wip_box
                    WHERE status = 1
                    AND locationId = '$locationId'
                    AND wipLineNumber = '$wipLineNumber'
                    AND stack = '$stack'");
        
      if(mysqli_num_rows($sql) > 0){
        while($row = $sql->fetch_array()){
             $response["responses"] = TRUE;
        }
            echo json_encode($response);
            
        }else{
            $response["responses"] = FALSE;
            echo json_encode($response);
        }
    }

?>
