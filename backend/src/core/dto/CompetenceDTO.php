<?php

namespace charly\core\dto;

class CompetenceDTO extends DTO
{   
    protected int $id;
    protected string $libelle;
    protected string $description;

    public function __construct(string $libelle, string $description, int $id)
    {
        $this->libelle = $libelle;
        $this->description = $description;
        $this->id = $id;
    }
}