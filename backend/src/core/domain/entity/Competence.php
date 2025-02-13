<?php

namespace charly\core\domain\entity;

class Competence 
{   
    protected string $libelle;
    protected string $description;

    public function __construct(string $libelle, string $description)
    {
        $this->libelle = $libelle;
        $this->description = $description;
    }

    public function getLibelle(): string
    {
        return $this->libelle;
    }

    public function getDescription(): string
    {
        return $this->description;
    }

}