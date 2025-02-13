<?php

namespace charly\core\dto;

class CompetenceWithInterestDTO extends DTO
{
    protected string $id;
    protected string $label;
    protected int $interest;

    public function __construct(string $id, string $label, int $interest)
    {
        $this->id = $id;
        $this->label = $label;
        $this->interest = $interest;
    }

    public function getId(): string
    {
        return $this->id;
    }

    public function getLabel(): string
    {
        return $this->label;
    }

    public function getInterest(): int
    {
        return $this->interest;
    }
}