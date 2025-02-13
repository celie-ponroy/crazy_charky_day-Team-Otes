<?php

namespace charly\application\action;

use charly\application\renderer\JsonRenderer;
use charly\core\service\interfaces\CompetenceServiceInterface;
use Psr\Http\Message\ResponseInterface;
use Psr\Http\Message\ServerRequestInterface;
use Throwable;

class GetListCompetence extends AbstractAction
{
    protected CompetenceServiceInterface $competenceService;

    public function __construct(CompetenceServiceInterface $competenceService)
    {
        $this->competenceService = $competenceService;
    }

    public function __invoke(ServerRequestInterface $rq, ResponseInterface $rs, array $args): ResponseInterface
    {
        try {
            $competences = $this->competenceService->getListCompetence();
            
            if (!is_array($competences)) {
                throw new \RuntimeException("Invalid response from competence service.");
            }
            
            return JsonRenderer::render($rs, 200, $competences);
        } catch (Throwable $e) {
            return JsonRenderer::render($rs, 500, ["error" => "Une erreur interne est survenue.", "details" => $e->getMessage()]);
        }
    }
}