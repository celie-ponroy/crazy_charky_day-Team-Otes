<?php

namespace charly\core\domain\entity;
use Ramsey\Uuid\Guid\Guid;

class User extends Entity
{
    const ROLE_ADMIN = 2;
    const ROLE_SALARIE = 1;
    const ROLE_CLIENT = 0;

    protected string $nom;
    protected int $role;
    // protected string $password;

    public function __construct(string $nom, string $password)
    {   
        $this->setID(Guid::uuid4()->toString());
        $this->nom = $nom;
        $this->role = self::ROLE_CLIENT;
        // $this->password = $password;
    }
}