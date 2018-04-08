<?php
$db_name = "updish";
$username = "root";
$password = "root";
$server_name = "localhost";

try
{
  $conn = new PDO("mysql:dbname=$db_name;host=$server_name", $username, $password);
  $conn->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

}catch(Exception $e)
{
  echo 'Caught exception: ',  $e->getMessage(), "\n";
}


?>
