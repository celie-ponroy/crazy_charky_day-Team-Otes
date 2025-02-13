<?php

namespace charly\core\dto;

class CompetenceDTO extends DTO
{
    protected string $label;
    protected string $id;

    public function __construct(string $label, string $id)
    {
        $this->label = $label;
        $this->id = $id;
    }
}