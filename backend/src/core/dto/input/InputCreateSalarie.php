<?php

namespace charly\core\dto\input;

class InputCreateSalarie
{
    protected string $nom;
    protected array $competences;
    protected string $password;

    public function __construct(string $nom, array $competences, string $password)
    {
        $this->nom = $nom;
        $this->competences = $competences;
        $this->password= $password;
    }

    public function getNom(): string
    {
        return $this->nom;
    }

    public function getCompetences(): array
    {
        return $this->competences;
    }

    public function getPassword(): string
    {
        return $this->password;
    }

}