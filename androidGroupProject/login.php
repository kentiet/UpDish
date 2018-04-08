<?php
require "conn.php";

$user_name = $_POST['username'];
$user_pass = $_POST['password'];

$sql_qry = "SELECT * FROM account WHERE username like '$user_name' AND password like '$user_pass';";

$getAccountQry = $conn->prepare($sql_qry);
$getAccountQry->execute();

$result = $getAccountQry->fetchAll(PDO::FETCH_ASSOC);

if(count($result) > 0)
{
  echo "Login success";
}else
{
  echo "Login not success";
}
?>
