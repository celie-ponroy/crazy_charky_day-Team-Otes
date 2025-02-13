<?php

namespace charly\core\service;

use charly\core\dto\UserDTO;
use charly\core\service\interfaces\UserAuthServiceInterface;
use charly\infrastructure\repository\interfaces\UserRepositoryInterface;

class UserAuthService implements UserAuthServiceInterface
{
    protected UserRepositoryInterface $userRepository;

    public function __construct(UserRepositoryInterface $userRepository)
    {
        $this->userRepository = $userRepository;
    }

    public function signIn(string $nom, string $plainPassword): UserDTO
    {
        $user = $this->userRepository->getUserByNom($nom);
        if (!$user) {
            throw new \Exception("Utilisateur non trouvé");
        }
        if (!$user->verifyPassword($plainPassword)) {
            throw new \Exception("Mot de passe invalide");
        }
        return $user->toDTO();
    }
}