<?php

namespace charly\core\service;

use charly\core\dto\input\InputCreateSalarie;
use charly\core\dto\UserDTO;
use charly\core\service\interfaces\UserServiceInterface;
use charly\infrastructure\repository\interfaces\UserRepositoryInterface;

class UserService implements UserServiceInterface
{
    protected UserRepositoryInterface $userRepository;

    public function __construct(UserRepositoryInterface $userRepository)
    {
        $this->userRepository = $userRepository;
    }
    public function createSalarie(InputCreateSalarie $input): UserDTO
    {
        $salarie = $this->userRepository->createSalarie($input);
        return $salarie->toDTO();
    }

    public function getSalaries(): array
    {
        return $this->userRepository->getSalaries();
    }
    public function getUserBesoins (string $id): array
    {
        return $this->userRepository->getUserBesoins($id);
    }

    public function getBesoins (): array
    {
        return $this->userRepository->getBesoins();
    }
 
}