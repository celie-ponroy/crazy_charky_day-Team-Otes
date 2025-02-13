<?php

namespace charly\core\domain\entity;

use charly\core\dto\UserDTO;

class User
{
    protected string $nom;
    protected string $id;
    protected int $role;
    protected string $password;

    public function __construct(string $id, string $nom, int $role, string|null $password=null)
    {
        $this->id = $id;
        $this->nom = $nom;
        $this->role = $role;
        $this->password = $password;
    }
    public function toDTO(): UserDTO{
        return new UserDTO($this->nom, $this->id, $this->role);
    }

    public function verifyPassword(string $plainPassword): bool
    {
        return password_verify($plainPassword, $this->password);
    }

}