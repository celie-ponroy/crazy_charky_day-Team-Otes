<?php

namespace charly\core\domain\entity;

use charly\core\dto\CompetenceDTO;

class Competence
{
    protected string $label;
    protected string $id;

    public function __construct(string $label, string $id)
    {
        $this->label = $label;
        $this->id = $id;
    }

   public function toDTO(): CompetenceDTO
    {
        return new CompetenceDTO($this->label, $this->id);
    }

}