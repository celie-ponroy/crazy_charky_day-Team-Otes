<?php

namespace charly\application\action;

use charly\application\renderer\JsonRenderer;
use charly\core\dto\input\InputCreateBesoinDTO;
use charly\core\service\BesoinService;
use charly\core\service\interfaces\BesoinServiceInterface;
use Psr\Http\Message\ResponseInterface;
use Psr\Http\Message\ServerRequestInterface;

class PostBesoin extends AbstractAction
{

    protected BesoinServiceInterface $besoinService;

    public function __construct(BesoinServiceInterface $bs)
    {
        $this->besoinService = $bs;
    }
    public function __invoke(ServerRequestInterface $rq, ResponseInterface $rs, array $args): ResponseInterface
    {
       $data = $rq->getParsedBody();

        //nom client, id competence, libelle besoin,
        $nomClient = $data['nom_client'];
        $idCompetence = $data['id_competence'];
        $libelleBesoin = $data['libelle_besoin'];


        $besoin = $this->besoinService->createBesoin(new InputCreateBesoinDTO($nomClient, $idCompetence, $libelleBesoin));
        return JsonRenderer::render($rs, 200, $besoin);
    }
}