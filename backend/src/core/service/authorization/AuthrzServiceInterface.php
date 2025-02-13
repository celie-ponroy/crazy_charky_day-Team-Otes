<?php   
namespace charly\core\service\authorization;
interface AuthrzServiceInterface{
    public function isGranted(string $userid): bool;
}