<?php

namespace charly\core\dto;

class UserDTO extends DTO
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

    public function getNom(): string
    {
        return $this->nom;
    }

    public function getId(): string
    {
        return $this->id;
    }

    public function getRole(): int
    {
        return $this->role;
    }
}