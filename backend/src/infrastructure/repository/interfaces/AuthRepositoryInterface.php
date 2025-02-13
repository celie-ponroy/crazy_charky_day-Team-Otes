<?php

namespace charly\infrastructure\repository\interfaces;

use charly\core\dto\UserDTO;
use charly\core\domain\entity\User;

interface AuthRepositoryInterface {
    public function register (User $user): void; // enregistre un utilisateur
    public function login(string $nom): ?UserDTO; // Authentifie un utilisateur par son email
    public function getUserById(string $id): ?UserDTO; // Récupère un utilisateur par son id
}