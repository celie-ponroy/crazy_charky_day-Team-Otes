<?php

namespace charly\core\service\interfaces;
use charly\core\dto\UserDTO;

interface UserAuthServiceInterface
{
    public function register(string $nom, string $password): void; // Methode pour enregistrer un utilisateur
    public function login(string $nom, string $password): UserDTO; // Methode pour authentifier un utilisateur
}