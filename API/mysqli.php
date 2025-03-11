<?php

     define('_HOST_NAME','localhost');
     define('_DATABASE_NAME','wipinformationsystem');
     define('_DATABASE_USER_NAME','root');
     define('_DATABASE_PASSWORD','');

    //  define('_HOST_NAME','192.168.1.9');
    //  define('_DATABASE_NAME','wipinformationsystem');
    //  define('_DATABASE_USER_NAME','andon');
    //  define('_DATABASE_PASSWORD','4nd0n');
 
     $MySQLiconn = new MySQLi(_HOST_NAME,_DATABASE_USER_NAME,_DATABASE_PASSWORD,_DATABASE_NAME);
  
     if($MySQLiconn->connect_errno) {
       die("ERROR : -> ".$MySQLiconn->connect_error);
     }

?>