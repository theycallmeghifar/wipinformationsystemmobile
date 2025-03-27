<?php
require_once 'mysqli.php';

    $response = array();

    if ($_SERVER['REQUEST_METHOD'] == 'POST') {
        
        $username = $_POST['username'];
        $password = $_POST['password'];

        $sql = $MySQLiconn->query("SELECT username, password, role
                  FROM user
                  WHERE username = '$username' and password = '$password'");
        
      if(mysqli_num_rows($sql) > 0){
        while($row = $sql->fetch_array()){
             $response["responses"] = TRUE;
             $response["role"] = (int) $row["role"];
        }
            echo json_encode($response);
            
        }else{
            $response["responses"] = FALSE;
            echo json_encode($response);
        }
    }

?>
