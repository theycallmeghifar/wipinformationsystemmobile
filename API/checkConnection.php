<?php
require_once 'mysqli.php';

    $response = array();

    if ($_SERVER['REQUEST_METHOD'] == 'GET') {

        $sql = $MySQLiconn->query("SELECT * FROM user");
        
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
