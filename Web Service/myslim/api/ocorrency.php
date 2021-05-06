<?php

//Get All Ocorrences
$app->get('/api/ocorrency/getAllOcorrences', function () {
	require_once('db/dbconnect.php');

	foreach ($db->ocorrency() as $row) {
		$data[] = $row;
	}
	echo json_encode($data, JSON_UNESCAPED_UNICODE);
});

//Get Ocorrency By id
$app->get('/api/ocorrency/getOcorrency/{id}', function ($request) {
	$id = $request->getAttribute('id');
	require_once('db/dbconnect.php');

	foreach ($db->ocorrency()
		->select('*')
		->where('id', $id)
		as $row) {
		$data[] = $row;
	}

	if (isset($data)) {
		echo json_encode($data, JSON_UNESCAPED_UNICODE);
	} else {
		echo json_encode("Ocorrencia nao encontrada", JSON_UNESCAPED_UNICODE);
	}
});

//Delete Ocorrency By id
$app->delete('/api/ocorrency/getOcorrency/{id}', function ($request) {
	$id = $request->getAttribute('id');

	require_once('db/dbconnect.php');
	$ocorrency = $db->ocorrency()[$id];
	if ($ocorrency) {
		$result = $ocorrency->delete();
		if ($result) {
			$result = ['status' => true, 'MSG' => 'Ocorrencia paagada com sucesso'];
			echo json_encode($result, JSON_UNESCAPED_UNICODE);
		} else {
			$result = ['status' => false, 'MSG' => 'Erro ao apagar Ocorrencia'];
			echo json_encode($result, JSON_UNESCAPED_UNICODE);
		}
	}else{
		$result=['status' =>false, 'MSG'=>'Ocorrencia nao existe'];
	}
});

//Get All Ocorrences by Category
$app->get('/api/ocorrency/getAllOcorrencesByCategory/{category}', function () {
	require_once('db/dbconnect.php');

	foreach ($db->ocorrency() as $row) {
		$data[] = $row;
	}
	echo json_encode($data, JSON_UNESCAPED_UNICODE);
});

//Get All Ocorrences by Distance
$app->get('/api/ocorrency/getAllOcorrencesFilteredIDK', function () {
	require_once('db/dbconnect.php');

	foreach ($db->ocorrency() as $row) {
		$data[] = $row;
	}
	echo json_encode($data, JSON_UNESCAPED_UNICODE);
});

//Get Ocorrency by User
$app->get('/api/ocorrency/ocorrencyByUser/{id}', function ($request) {
	$id = $request->getAttribute('id');
	require_once('db/dbconnect.php');

	foreach ($db->ocorrency()
		->Select('*')
		->Where('user_id', $id) as $row) {
		$data[] = $row;
	}
	echo json_encode($data, JSON_UNESCAPED_UNICODE);
});

//Put Ocorrency by User
$app->put('/api/ocorrency/changeOcorrencyInfo/{id}', function ($request) {

	require_once('db/dbconnect.php');
	$id = $request->getAttribute('id');

	$category_id = $request->getParsedBody()['category_id'];
	// $foto=$request->getParsedBody()['foto'];
	$street = $request->getParsedBody()['street'];
	$reference_point = $request->getParsedBody()['reference_point'];
	$description = $request->getParsedBody()['description'];

	$data = array(
		"category_id" => $category_id,
		"street" => $street,
		"reference_point" => $reference_point,
		"description" => $description
	);

	if (isset($db->ocorrency[$id])) {
		$result = $db->ocorrency[$id]->update($data);
		if ($result) {
			echo json_encode(("Alteracoes efetuadas com sucesso"), JSON_UNESCAPED_UNICODE);
		} else {
			echo json_encode(("Erro na alteracao dos dados"), JSON_UNESCAPED_UNICODE);
		}
	} else {
		echo json_encode("Esta ocorrencia nao existe", JSON_UNESCAPED_UNICODE);
	}
});

//Add Ocorrency
$app->post('/api/ocorrency/addNewOcorrency', function () {
	require_once('db/dbconnect.php');
	$category_id = $_POST["category_id"];
	$user_id = $_POST["user_id"];
	$foto = $_POST["foto"];
	$street = $_POST["street"];
	$reference_point = $_POST["reference_point"];
	$description = $_POST["description"];
	$latitude = $_POST["latitude"];
	$longitude = $_POST["longitude"];
	$date = $_POST["date"];


	$ocorrency = $db->ocorrency();

	$result = $ocorrency->insert(array(
		"category_id" => $category_id,
		"user_id" => $user_id,
		"foto"  => $foto,
		"street" => $street,
		"reference_point" => $reference_point,
		"description" => $description,
		"latitude" => $latitude,
		"longitude" => $longitude,
		"date" => $date
	));

	if ($result == false) {
		$result = ['status' => false, 'MSG' => 'Insercao da ocorrencia falhou'];
		echo json_encode($result, JSON_UNESCAPED_UNICODE);
	} else {
		$result = ['status' => true, 'MSG' => "Ocorrencia registada com sucesso!"];
		echo json_encode($result, JSON_UNESCAPED_UNICODE);
	}
});

//This is an example of a join with ocorrencyTBL street and userTBL username
// $app->get('/api/ocorrency', function () {
// 	require_once('db/dbconnect.php');
// 	$myArray = array();
// 	foreach ($db->ocorrency() as $row) {
// 		array_push($myArray, array(
// 			'ocorrency' => $row["street"],
// 			'user' => $row->user['username']
// 		));
// 	}
// 	echo json_encode($myArray, JSON_UNESCAPED_UNICODE);
// });
