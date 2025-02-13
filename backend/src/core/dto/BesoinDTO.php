<?php

namespace charly\core\dto;

use charly\core\domain\entity\Besoin;

class BesoinDTO extends DTO
{
    protected string $nomClient;
    protected string $libelleBesoin;
    protected int $idCompetence;
    protected int $id;

    public function __construct(Besoin $besoin)
    {
        $this->nomClient = $besoin->getNomClient();
        $this->libelleBesoin = $besoin->getLibelleBesoin();
        $this->idCompetence = $besoin->getIdCompetence();
        $this->id = $besoin->getId();
    }

}