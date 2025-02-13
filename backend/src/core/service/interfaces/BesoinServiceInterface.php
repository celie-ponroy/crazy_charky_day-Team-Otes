<?php

namespace charly\core\service\interfaces;

use charly\core\dto\BesoinDTO;
use charly\core\dto\input\InputCreateBesoinDTO;

interface BesoinServiceInterface
{
    public function createBesoin(InputCreateBesoinDTO $inputCreateBesoinDTO): BesoinDTO;
}