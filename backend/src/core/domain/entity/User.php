<?php

namespace charly\core\domain\entity;

use charly\core\dto\UserDTO;

class User
{
    protected string $nom;
    protected string $id;
    protected int $role;

    public function __construct(string $nom, string $id, int $role)
    {
        $this->nom = $nom;
        $this->id = $id;
        $this->role = $role;
    }

    public function toDTO(): UserDTO{
        return new UserDTO($this->nom, $this->id, $this->role);
    }

}