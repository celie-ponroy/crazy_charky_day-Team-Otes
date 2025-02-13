<?php

namespace charly\core\service\authorization;

use charly\core\domain\entity\User;
use charly\core\service\interfaces\UserAuthServiceInterface;

class AuthrzService implements AuthrzServiceInterface
{

    private UserAuthServiceInterface $authRepository;

    public function __construct(UserAuthServiceInterface $authRepository)
    {
        $this->authRepository = $authRepository;
    }

    public function isGranted(string $userId): bool
    {   
        $userDTO = $this->authRepository->getUserById($userId);
        if ($userDTO->role !== User::ROLE_ADMIN) {
            throw new AuthrzInvalidRoleException("Vous n'avez pas les droits pour effectuer cette action.");
        }

        return true;
    }
    
}
