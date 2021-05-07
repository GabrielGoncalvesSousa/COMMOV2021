<?php

//Get All Users
$app->get('/api/user/getAllUsers', function () {
	require_once('db/dbconnect.php');

	foreach ($db->user() as $row) {
		$data[] = $row;
	}
	echo json_encode($data, JSON_UNESCAPED_UNICODE);
});

//Get user by id
$app->get('/api/user/getUser/{id}', function ($request) {
	$id = $request->getAttribute('id');
	require_once('db/dbconnect.php');

	foreach ($db->user()
		->select('first_name', 'last_name')
		->where('id', $id)
		as $row) {
		$data[] = $row;
	}

	if (isset($data)) {
		echo json_encode($data, JSON_UNESCAPED_UNICODE);
	} else {
		echo json_encode("Utilizador nao encontrado", JSON_UNESCAPED_UNICODE);
	}
});

// Get by username and password, aka LOGIN
$app->get('/api/user/login/{username}&&{password}', function ($request) {
	$username = $request->getAttribute('username');
	$password = $request->getAttribute('password');

	require_once('db/dbconnect.php');
	foreach ($db->user()
		->select('*')
		->where('username', $username)
		->and('password', $password)
		as $row) {
		$data[] = $row;
	}

	if (isset($data)) {
		echo json_encode($data, JSON_UNESCAPED_UNICODE);
		echo ("\nLogin com sucesso");
	} else {
		echo json_encode("Username ou palavra passe errados.", JSON_UNESCAPED_UNICODE);
	}
});

// Add User
$app->post('/api/user/addNewUser', function () {
	require_once('db/dbconnect.php');
	$username = $_POST["username"];
	$first_name = $_POST["first_name"];
	$last_name = $_POST["last_name"];
	$email = $_POST["email"];
	$password = $_POST["password"];
	$notifications = 0;

	$user = $db->user();

	$result = $user->insert(array(
		"username" => $username,
		"first_name" => $first_name,
		"last_name"  => $last_name,
		"email" => $email,
		"password" => $password,
		"notifications" => $notifications
	));

	if ($result == false) {
		$result = ['status' => false, 'MSG' => 'Insercao dos dados falhou'];
		echo json_encode($result, JSON_UNESCAPED_UNICODE);
	} else {
		$result = ['status' => true, 'MSG' => "Conta registada com sucesso!"];
		echo json_encode($result, JSON_UNESCAPED_UNICODE);
	}
});

//Edit User firstname nd last name, email, activate notifications, maybe change password
$app->put('/api/user/changeUserInfo/{id}', function ($request) {
	
	require_once('db/dbconnect.php');
	$id= $request->getAttribute('id');

	$first_name=$request->getParsedBody()['first_name'];
	$last_name=$request->getParsedBody()['last_name'];
	$email=$request->getParsedBody()['email'];
	// $password=$request->getParsedBody()['password'];
	$notifications=$request->getParsedBody()['notifications'];

	$data=array(
		"first_name"=>$first_name,
		"last_name"=>$last_name,
		"email" =>$email,
		// "password"=>$password,
		"notifications" =>$notifications
	);

	if(isset($db->user[$id])){
		$result = $db ->user[$id]->update($data);
		if($result){
			echo json_encode(("Alteracoes efetuadas com sucesso"),JSON_UNESCAPED_UNICODE);
		}else{
			echo json_encode(("Erro na alteracao dos dados"),JSON_UNESCAPED_UNICODE);			
		}
	}else{
		echo json_encode("Utilizador nao existe",JSON_UNESCAPED_UNICODE);
	}
});
