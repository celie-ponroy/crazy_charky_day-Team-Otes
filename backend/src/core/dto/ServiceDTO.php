<?php
namespace charly\core\dto;

use charly\core\dto\DTO;

class ServiceDTO extends DTO {
    protected int $id;
    protected string $clientid;
    protected string $salarieid;
    protected int $idbesoin;

    public function __construct(int $id, string $clientid, string $salarieid, int $idbesoin) {
        $this->id = $id;
        $this->clientid = $clientid;
        $this->salarieid = $salarieid;
        $this->idbesoin = $idbesoin;
    }
}
