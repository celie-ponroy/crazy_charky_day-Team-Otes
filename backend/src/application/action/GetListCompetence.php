<?php

namespace charly\application\action;

use charly\application\renderer\JsonRenderer;
use charly\core\service\interfaces\CompetenceServiceInterface;
use Psr\Http\Message\ResponseInterface;
use Psr\Http\Message\ServerRequestInterface;

class GetListCompetence extends AbstractAction
{
    protected CompetenceServiceInterface $competenceService;

    public function __construct(CompetenceServiceInterface $competenceService)
    {
        $this->competenceService = $competenceService;
    }

    public function __invoke(ServerRequestInterface $rq, ResponseInterface $rs, array $args): ResponseInterface
    {
        $competences = $this->competenceService->getListCompetence();
        return JsonRenderer::render($rs, 200, $competences);
    }
}