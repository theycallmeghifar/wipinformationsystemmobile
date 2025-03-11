<?php
require_once 'mysqli.php';

    $response = array();

    if ($_SERVER['REQUEST_METHOD'] == 'POST') {
        
        $username = $_POST['username'];
        $password = $_POST['password'];

        $sql1 = $MySQLiconn->query("SELECT username, password
                  FROM user
                  WHERE username = '$username' and password = '$password' AND role IN (1,2)");
        
      if(mysqli_num_rows($sql1) > 0){
        while($row = $sql1->fetch_array()){
             $response["responses"] = TRUE;
        }
            echo json_encode($response);
            
        }else{
            $response["responses"] = FALSE;
            echo json_encode($response);
        }
    }

?>
