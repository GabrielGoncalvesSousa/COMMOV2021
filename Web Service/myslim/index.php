<?php

use Psr\Http\Message\ResponseInterface as Response;
use Psr\Http\Message\ServerRequestInterface as Request;

require 'vendor/autoload.php';

$app = new \Slim\App();

require_once('api/user.php');
require_once('api/ocorrency.php');


$app->run();
