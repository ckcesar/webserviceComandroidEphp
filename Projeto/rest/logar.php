<?php

require("conexaopdo.php");
$con = new Conexao();

//Recebendo valor do usuário
$email = $_POST['usuario'];
$senha = $_POST['senha'];

$sql = $con->listar("select id, email, password_hash, customer_id from user where email='$email' and password_hash='$senha' limit 1");
if($sql){
    foreach($sql as $linha){
        echo'['. json_encode($linha) . ']';
    }
}else{
    echo"0";
}